#!/bin/bash
#
# bb - a file-system based bug/issue tracking system for people who hate bug tracking systems.
#
# TODO
# - move arg handling (other than action) into each action case
# - action to remove tag (no change tag. chage = remove + add)
# - walk up dir tree to find non-root-based BB_BUGDIR (i.e., if ".bb" instead of "/home/me/projects/foo/.bb")
# - walk up dir tree to find config file inside bugdir and source it
# - support short-form IDs (specify only as much as needed to disambiguate)
# - fix add bug w/bug info spec-ed on cmdline args
#

set -e
set -u

# quick fail if unset (see "set -u")
FOO=$BB_USER
FOO=$BB_BUGDIR
unset FOO

COLOR_reset="[0m"
COLOR_red="[31m"

# pick the sha do-er for our OS
if [[ "$(uname -s)" = "Darwin" ]]
then
    SHA=shasum   # OSX
else
    SHA=sha1sum  # Linux/etc
fi

NEWID=$($SHA<<< "$(date)-$RANDOM-$BB_USER" | awk '{print $1}')

if [[ ! -d "$BB_BUGDIR" ]]
then
    mkdir -p "$BB_BUGDIR"
fi


cd "$BB_BUGDIR"     # IMPORTANT - ensures the dir exists and simplifies the rest of the script


# temporarily disable check in case too few args given
set +u
ACTION=$1
ID=$2
SEARCH=$2
set -u

if [[ -n "$ACTION" ]]
then
    shift # remove action, if present
fi

if [[ -n "$ID" ]]
then
    shift
else
    ID=$NEWID
fi

REST=$@

function error
{
    echo -e 1>&2 "${COLOR_red}$@${COLOR_reset}"
}

function die
{
  if [[ ! -z "$@" ]]
  then
    error "$@"
  fi
  exit 127
}

function require_valid_id() {
    if [[ ! -f "$ID" ]]
    then
        die "$ID not a valid id"
    fi
}

function main() {
    if [[ "add" = "$ACTION" || "new" = "$ACTION" || "a" = "$ACTION" ]]
    then
        ID=$NEWID

        if [ -f "$ID" ]
        then
            # TODO recompute new id instead
            die "internal error - try again"
        fi

        echo $ID
        if [ -n "$REST" ]
        then
            echo "$REST" >> $ID # BUG: $REST is probably missing the first word - eaten up by $ID (improperly)
        else
            if [ -t 0 ] # check stdin, not stdout bc it'll be affected by pipes used to create bugs
            then
                echo "^D to exit" >&2
            fi

            echo "tag:created:$(date)" >> $ID # just a convenience - is this feature creep?
            cat >> $ID
        fi

    elif [[ "edit" = "$ACTION" || "e" = "$ACTION" ]]
    then

        $EDITOR "$ID" # doesn't need to exist yet (ok to use edit to add)
        # TODO won't have a created date if made this way

    elif [[ "list" = "$ACTION" ]]
    then

        ls

    elif [[ "tag" = "$ACTION" ]]
    then

        TAG=$REST
        require_valid_id
        if [[ -n "$TAG" ]]
        then
            sed -i $ID -e "1itag:$TAG"
        else
            die "no tag given"
        fi

    elif [[ "search" = "$ACTION" || "grep" = "$ACTION" ]]
    then

        egrep "$SEARCH" * # quote search so interp as single arg and not interfere w/* file-spec

    else

        $0 list # default action

    fi
}

main

