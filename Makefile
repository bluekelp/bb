.PHONY: all install clean test retest

SHELL=/bin/bash

INSTALL_DIR=~/bin
SCRIPT=bb
DATA_DIR=/tmp/bb-bugs

TARGET=$(INSTALL_DIR)/$(SCRIPT)

all: install

$(TARGET): $(SCRIPT)
	@cp $(SCRIPT) $(INSTALL_DIR)
	@echo "installing to $(INSTALL_DIR)"

install: $(TARGET)

clean:
	rm -rf $(DATA_DIR)
	rm -f $(TARGET)

test:
	SCRIPT=$(TARGET) BB_BUGDIR=$(DATA_DIR) BB_USER=test-user ./test-script

retest: clean install test
