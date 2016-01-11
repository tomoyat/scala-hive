# Scala-hive

## re-generate thrift code

```
$ wget https://raw.githubusercontent.com/apache/hive/release-1.0.1/metastore/if/hive_metastore.thrift -O if/hive_metastore.thrift
$ wget https://raw.githubusercontent.com/apache/hive/release-1.0.1/service/if/TCLIService.thrift -O if/TCLIService.thrift
$ wget https://raw.githubusercontent.com/apache/thrift/master/contrib/fb303/if/fb303.thrift -O if/fb303.thrift
```

edit `if/hive_metastore.thrift`

```
- include "share/fb303/if/fb303.thrift"
+ include "if/fb303.thrift"
```


```
$ thrift -I if/fb303.thrift --gen java:beans,hashcode -o src/main/gen/1.0.1 if/hive_metastore.thrift
$ thrift -I if/fb303.thrift --gen java:beans,hashcode -o src/main/gen/1.0.1 if/TCLIService.thrift
$ thrift -I if/fb303.thrift --gen java:beans,hashcode -o src/main/gen/1.0.1 if/fb303.thrift
```

create `idea.properties`

https://www.jetbrains.com/idea/help/file-idea-properties.html

```
#---------------------------------------------------------------------
# Maximum file size (kilobytes) IDE should provide code assistance for.
# The larger file is the slower its editor works and higher overall system memory requirements are
# if code assistance is enabled. Remove this property or set to very large number if you need
# code assistance for any files available regardless their size.
#---------------------------------------------------------------------
idea.max.intellisense.filesize=250000
```


