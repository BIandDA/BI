APP    = target-app
SCOPE  = user99
TAG    = $(shell echo "$$(date +%F)-$$(git rev-parse --short HEAD)")
ENV    = staging

help:
	@echo "Run make <target> where target is one of the following..."
	@echo
	@echo "    pip         - install required libraries"
	@echo "    lint        - run flake8 and pylint"
	@echo "    unittest    - run unittests"
	@echo "    build       - build docker container"
	@echo "    run         - run containter on host port 5000"
	@echo "    interactive - run container interactively on host port 5000"
	@echo "    upload      - run ./upload-new-version.sh"
	@echo "    deploy      - run ./deploy-new-version.sh staging"
	@echo "    test        - run ./test-environment.sh staging"
	@echo "    clean       - stop local container, clean up workspace"
	@echo
	@echo "For the production environment...."
	@echo
	@echo "    deploy ENV=production - run ./deploy-new-version.sh production"
	@echo "    test ENV=prodiction   - run ./test-environment.sh production"
	@echo
	@echo "FYI, Current tag is $(TAG)"
pip:
	pip install --quiet --upgrade --requirement requirements.txt

lint:
	flake8 --ignore=E501,E231 *.py tests/*.py
	pylint --errors-only --disable=C0301 --disable=C0326 *.py tests/*.py

unittest: lint
	python -m unittest --verbose --failfast

build: unittest
	docker build -t $(SCOPE)/$(APP):$(TAG) .

run:
	docker run --rm -d -p 5000:5000 --name $(APP) $(SCOPE)/$(APP):$(TAG)

interactive:
	docker run --rm -it -p 5000:5000 --name $(APP) $(SCOPE)/$(APP):$(TAG)

upload: unittest
	./upload-new-version.sh

test:
	./test-environment.sh $(ENV)

deploy: upload
	./deploy-new-version.sh $(ENV)

clean:
	docker container stop $(APP) || true
	@rm -rf ./__pycache__ ./tests/__pycache__
	@rm -f .*~ *.pyc

.PHONY: build clean deploy help interactive lint pip run test unittest upload
