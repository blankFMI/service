#!/usr/bin/env bash
#
# run_performance_test.sh
#
# Usage examples
#   ./run_performance_test.sh                               # uses defaults below
#   ./run_performance_test.sh my-host.dev 443 https result  # override everything
#
# Positional args (all optional):
#   $1 – host      (default: turbo-parakeet-qprg954j574h94-8081.app.github.dev)
#   $2 – port      (default: 443)
#   $3 – protocol  (default: https)
#   $4 – results   (default: jmeter/results.jtl)

set -euo pipefail

# https://turbo-parakeet-qprg954j574h94-8080.app.github.dev/

HOST="${1:-turbo-parakeet-qprg954j574h94-8080.app.github.dev}"
PORT="${2:-443}"
PROTO="${3:-https}"
RESULTS="${4:-jmeter/results.jtl}"

JMETER_BIN="./apache-jmeter-5.6.3/bin/jmeter"
TEST_PLAN="jmeter/performance_test.jmx"

exec "$JMETER_BIN" \
  -n -t "$TEST_PLAN" \
  -Jhost="$HOST" \
  -Jport="$PORT" \
  -Jprotocol="$PROTO" \
  -l "$RESULTS"
