### 插件调试

1. 建立Remote Configuration

2. 把插件本地发布然后接入到app工程里之后，在命令行执行构建命令，末尾拼上这一串参数：`-Dorg.gradle.debug=true --no-daemon`，比如，
   
   `gradle clean :app:assembleRelease -Dorg.gradle.debug=true --no-daemon`

3. 然后切换到刚刚创建的Remote Configuration，打断点，点击debug按钮开始调试


