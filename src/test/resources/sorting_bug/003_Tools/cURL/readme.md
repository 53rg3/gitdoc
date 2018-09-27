<!--- FILE_TOC -->
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[0.1 cURL](#curl)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[0.1.1 Commonly used options](#commonly-used-options)<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;[0.1.2 HTTPRequests](#httprequests)<br>
<!--- TOC_END -->




## cURL

Usage:


```BASH
curl [options...] <url>
```

### Commonly used options

* There is no need for a whitespace between the option and it's value, i.e. `-XPUT` works as `-X PUT`.
* You need to specify the option every time, if you need it multiple times, i.e. `-H 'header1: asdf' -H 'header2: qwer'`.
* Option values must be enclosed in single quotes, i.e. `-d {id: 123, name: Bob}`.



```BASH
-d, --data (HTTP POST data)
-X, --request (HTTP request method, i.e. GET etc)
-H, --header (Pass custom header LINE to server)


```

### HTTPRequests

Use option `-XMETHOD`, e.g. `-XGET`, `-XPOST`, `-XPUT`, `-XDELETE`


Example: (Notice the single quotes enclosing the value after `-d`)


```BASH
curl -XPUT 'localhost:9200/twitter/_mapping/user?pretty' -H 'Content-Type: application/json' -d'
{
  "properties": {
    "name": {
      "type": "text"
    }
  }
}
'
```

