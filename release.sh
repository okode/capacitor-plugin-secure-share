#!/bin/bash
set -eo pipefail

npm version patch --tag-version-prefix=""
git push && git push --tags
