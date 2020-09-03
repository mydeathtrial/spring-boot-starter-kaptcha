# spring-boot-starter-kaptcha ： google-kaptcha验证码自动加载组件
[![freemark](https://img.shields.io/badge/freemark-LATEST-green)](https://img.shields.io/badge/freemark-LATEST-green)
[![maven](https://img.shields.io/badge/build-maven-green)](https://img.shields.io/badge/build-maven-green)
## 它有什么作用

* **spring-boot-starter配置加载**
组件通过spring-boot-starter方式加载

* **支持集群/分布式**
验证码生成后，存于缓存，组件中的缓存部分由agile-cache缓存提供，当集群/分布式环境中，可将缓存配置为redis缓存，以此实现
无状态化服务

* **开放所有自定义配置**
开放了可以开放的所有配置项

* **自定义验证码访问地址**
可以定义验证码访问地址

* **自动过期**
验证码可以配置过期时间
-------
## 快速入门
开始你的第一个项目是非常容易的。

#### 步骤 1: 下载包
您可以从[最新稳定版本]下载包(https://github.com/mydeathtrial/spring-boot-starter-kaptcha/releases).
该包已上传至maven中央仓库，可在pom中直接声明引用

以版本spring-boot-starter-kaptcha-2.0.0.jar为例。
#### 步骤 2: 添加maven依赖
```xml
<dependency>
    <groupId>cloud.agileframework</groupId>
    <artifactId>spring-boot-starter-kaptcha</artifactId>
    <version>2.0.0</version>
</dependency>
```
#### 步骤 3: 开箱即用
```properties
//验证码组件开关
agile.kaptcha.enable=true
//验证码失效时间
agile.kaptcha.live-time=30s
//前后端传输验证码相关令牌参数
agile.kaptcha.token-header=V-CODE
//验证码访问地址
agile.kaptcha.url=/code

//google kaptcha 验证码原生配置，可按需自行扩展
agile.kaptcha.properties.kaptcha.background.clear.from=45,45,45
agile.kaptcha.properties.kaptcha.background.clear.to=45,45,45
agile.kaptcha.properties.kaptcha.noise.color=white
agile.kaptcha.properties.kaptcha.border=yes
agile.kaptcha.properties.kaptcha.border.color=white
agile.kaptcha.properties.kaptcha.textproducer.font.color=white
agile.kaptcha.properties.kaptcha.textproducer.font.size=40
agile.kaptcha.properties.kaptcha.image.width=125
agile.kaptcha.properties.kaptcha.image.height=45
agile.kaptcha.properties.kaptcha.textproducer.char.length=4
agile.kaptcha.properties.kaptcha.textproducer.font.names="微软雅黑"
agile.kaptcha.properties.kaptcha.obscurificator.impl=com.google.code.kaptcha.impl.ShadowGimpy
```
##### 自定义验证码内容
```properties
//自定义验证码内容
agile.kaptcha.text='我爱你中国'
//指定验证码内容生成器
agile.kaptcha.properties.kaptcha.textproducer.impl=cloud.agileframework.kaptcha.kaptcha.AgileTextProducer
```
#### 步骤 4: 直接访问
以上面的配置为例，访问地址为`http://localhost:8080/code`，访问后会在客户端会接收到验证码图片数据

#### 步骤 5: 后端获取当前请求的合法验证码
```
//同步访问中，将请求HttpServletRequest当作参数，组件会在请求中获取验证码令牌`agile.kaptcha.token-header`
//通过获取到的令牌，在缓存中提取已生成的合法验证码
KaptchaContextHolder.code(HttpServletRequest request)
```
