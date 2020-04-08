# 쉘 스크립트

- if 문 사용
`format: if [ -f "fileName"]; then else fi`
```shell script
file = "2020-04-08.log"
if [ -f "$file" ];
then
    echo "hello"
fi
```