author `gwf@sjychina.com`


### Windows 下打.dll文件
> PowerShell下执行
```shell
mkdir build
cd build
cmake ..
MSBuild minilzo_java.sln /t:Rebuild /p:Configuration=Release /p:Platform=x64
```
将生成的 Release 下的 minilzo_java.dll 复制至导入resources/libs的路径


### Linux 下打.so文件
> 命令行执行
```shell
mkdir build
cd build
cmake ..
make
```
将生成的native/build/libminilzo_java.so文件复制至导入resources/libs的路径