# Simple REST api to retrieve jokes

### System requirement
* JDK 8 or above
* Maven

### Start the application
Run this command at the root directory:
```bash
mvn exec:java -Dexec.mainClass="com.finx.demo.jokes.App" -Dexec.args="server config.yml"
```

### Test the API
The API is available at: `http://localhost:8080/jokes?keyword=<your-keyword>`, with `your-keyword` is the keyword to search for jokes.

### Data source
The jokes are sourced from [chucknorris.io](https://api.chucknorris.io).