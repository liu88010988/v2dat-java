## 本项目是java版本的geo数据解析，使用java 21

## 解析逻辑参考于[v2dat](https://github.com/urlesistiana/v2dat)

## 该项目仅为个人测试使用，如有侵权请联系，会及时调整

```shell
mvn clean package -Dmaven.test.skip=true -U
java -jar target/v2dat-java.jar -u geoip -d /path-to-out-dri -t private,cn -f /path-to-geo-file/geoip.dat -c true
```

### 参数说明

| 参数 | 描述                     | 是否必须           |
|----|------------------------|----------------|
| -u | 解析类型    geoip  geosite | 是              |
| -d | 解析后数据文件的写入目录           | 是              |
| -t | 过滤标识                   | 否，不传则解析生成所有tag |
| -f | geo原始数据的目录             | 是              |
| -c | 生成前是否清理输出目录            | 否，默认true       |