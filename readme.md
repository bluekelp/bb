bb
==

a file-system based bug/issue tracking system for people who hate bug tracking systems. the name is simple and easy to type. that is all.

by providing a small set of basic actions (*create*, *edit*, *tag*, *search*) we can build more complex actions on top and any desired workflow. when in doubt, let the user manually edit. **less is more.**
*way more.*

why?
----
*bb* is in reaction to other distributed tracking systems, which may lack such basic functionality as search (i'm looking at you, *bugs-everywhere*) and are written in fancy-pants languages.

most conventional tracking systems piss me off
- assume they'll run on a centralized server
- require a RDBMS
- often need someone to setup, support and maintain
- usually require you to use a web interface to do anything useful
- require logins
- are slow.  
 
and they like to email you stuff.

philosophy
----------
bb is built on the philosophy of *what's the simplest thing that could possibly work?*

**don't mess it up by piling features on** or you'll have created yet another *Jira* or *Bugzilla*.
- learn you some command line-fu
- write some scripts
- build on top
- don't make the base bigger
- note there are no priorities
- no assigned-to field
- no defined comment system
- build your own. *on top. not here.*

favor conventions over features. most problems we try and solve with workflow and custom fields are really **people problems**. the technical solution just makes everyone grumpy. badger those who don't do whatever it is your group has agreed upon or move them out of the group. don't
try to enforce this stuff in a tool.

use tags
--------
don't use custom fields. *tag:assigned-to:<someone>* is better than requiring an *assigned-to* field or command line arg
- build tools to search/filter/union/intersect/disjoin them.
- you'll be happer and have more powerful tools.

tips
----
a useful convention might be keeping the first non-tag line of the issue short and descriptive, skipping a line and putting the details and comments below -- like a *well formed git commit* (http://tbaggery.com/2008/04/19/a-note-about-git-commit-messages.html). note that *git* doesn't enforce convention either.

workflow
--------
unlike other distributed bug tracking systems (*bugs-everywhere*, *ditz*, etc.) bb makes no assumptions about a backing SCM like *git*, *mercurial*, or even *ssh+rsync*, or a suggested workflow.
- there is no commit action
- the workflow of the tool itself assumes nothing about how you sync/share/merge the files with others (or if you do)
- you're free to use this without backups in your local project
- without a SCM system
- or with one
- in your project's source-code repo
- in its own repo
- whatever
- you can commit updates to tickets in the branch your code is in and merge them when you merge your code
- you can have a separate repo like a stand-alone system and update bugs separately from code
- you should do what makes sense for your group
- you're smart
- you'll figure it out

License
----

GPLv3

