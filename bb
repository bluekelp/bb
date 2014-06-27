#!/bin/bash

source $HOME/.benv_script_fx
s_init

ME="$HOME-$HOSTNAME"
BUGDIR="$HOME/.bugs"

SHA=shasum # sha1sum on linux

NEWID=$($SHA<<< "$(date)-$RANDOM-$ME" | arg1)

if [[ ! -d "$BUGDIR" ]]
then
    mkdir -p "$BUGDIR"
fi

cd "$BUGDIR"

set +u
ACTION=$1
ID=$2
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

function require_valid_id() {
    if [[ ! -f "$ID" ]]
    then
        die "$ID not a valid id"
    fi
}

function main() {
    if [[ "add" = "$ACTION" || "new" = "$ACTION" || "a" = "$ACTION" ]]
    then
        # TODO make sure doesn't exist first, or warn/die
        echo "$ID -- ^D to exit" >&2
        cat >> $ID
    elif [[ "edit" = "$ACTION" || "e" = "$ACTION" ]]
    then
        $EDITOR "$ID" # doesn't need to exist yet (ok to use edit to add)
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
        grep $@ -- *
    else
        # default action
        #$0 list | grep "$ME"
        $0 list
    fi
}

main

#while getopts ":b:r:" opt
#do
#    case $opt in
#        (b)
#            branch="${OPTARG}"
#            ;;
#        (r)
#            remote="${OPTARG}"
#            ;;
#        (-)
#            # allows for '--' on cmdline to break arg processing
#            # (if '-' is present in getopts param str)
#            break
#            ;;
#        (\?)
#            usage
#            ;;
#    esac
#done
#
## remove parses args from cmdline
#shift $((OPTIND-1))

