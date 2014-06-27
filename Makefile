
.PHONY: all install

INSTALL_DIR=~/bin
SCRIPT=bb

all:

install:
	cp $(SCRIPT) $(INSTALL_DIR)
