# How to run tests

1) start CS and MSBill instances `docker compose up -d`

2) enter the msbill-test folder `cd msbill-tests`

3) run tests `./gradlew clean test`

4) detailed test report available under `msbill-tests/build/reports/tests/test/index.html`

5) stop and remove running instances `docker compose down`
