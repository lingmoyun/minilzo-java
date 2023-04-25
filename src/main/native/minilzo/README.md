author `winfordguo@gmail.com`


### 前提
> 打开终端
```shell
cd src/main/native/minilzo
```


### Windows 下打.dll文件
> PowerShell下执行
```shell
mkdir build
cd build
cmake ..
MSBuild minilzo_java.sln /t:Rebuild /p:Configuration=Release /p:Platform=x64
```
将生成的`build\Release\minilzo_java.dll`文件复制至`src/main/resources/libs`目录下


### Linux 下打.so文件
> 命令行执行
```shell
mkdir build
cd build
cmake ..
make
```
将生成的`build/libminilzo_java.so`文件复制至`src/main/resources/libs`目录下