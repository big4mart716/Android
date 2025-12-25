# 个人名片 - Android课程设计项目

## 项目简介

这是一个简单的Android应用程序，实现了个人信息管理功能。适合作为Android开发课程的课程设计项目。

## 功能特点

1. **欢迎页面** - 显示应用名称和简介
2. **信息输入页面** - 输入个人信息（姓名、学号、专业、电话、邮箱）
3. **信息展示页面** - 以名片形式展示输入的信息
4. **表单验证** - 确保所有字段都已填写
5. **友好的用户界面** - 使用Material Design风格

## 技术栈

- 开发语言: Java
- 最低SDK版本: Android 5.0 (API 21)
- 目标SDK版本: Android 13 (API 33)
- UI框架: AndroidX + Material Components

## 项目结构

```
PersonalCard/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/personalcard/
│   │   │   ├── MainActivity.java        # 主界面
│   │   │   ├── InputActivity.java       # 信息输入界面
│   │   │   └── DisplayActivity.java     # 信息展示界面
│   │   ├── res/
│   │   │   ├── layout/                  # 布局文件
│   │   │   └── values/                  # 资源文件
│   │   └── AndroidManifest.xml          # 应用配置
│   └── build.gradle                     # App级构建配置
├── build.gradle                         # 项目级构建配置
├── settings.gradle                      # 项目设置
└── gradle.properties                    # Gradle配置

```

## 如何生成APK文件

由于本机没有Android开发环境，以下提供三种生成APK的方法：

### 方法一：使用Android Studio（推荐）

1. 下载并安装Android Studio: https://developer.android.com/studio
2. 打开Android Studio，选择"Open an Existing Project"
3. 选择PersonalCard文件夹
4. 等待Gradle同步完成
5. 点击菜单 Build > Build Bundle(s) / APK(s) > Build APK(s)
6. 生成的APK位于: `app/build/outputs/apk/debug/app-debug.apk`

### 方法二：使用命令行（需要安装JDK和Android SDK）

```bash
cd PersonalCard
# 第一次运行需要下载Gradle
chmod +x gradlew
./gradlew assembleDebug
```

生成的APK位于: `app/build/outputs/apk/debug/app-debug.apk`

### 方法三：使用在线构建服务（无需本地环境）

#### 选项A：GitHub Actions（推荐）

1. 将项目上传到GitHub仓库
2. 在项目根目录创建 `.github/workflows/build.yml`:

```yaml
name: Android Build

on:
  push:
    branches: [ main ]
  workflow_dispatch:

jobs:
  build:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v3
      - name: Set up JDK 11
        uses: actions/setup-java@v3
        with:
          java-version: '11'
          distribution: 'temurin'
      - name: Grant execute permission for gradlew
        run: chmod +x gradlew
      - name: Build with Gradle
        run: ./gradlew assembleDebug
      - name: Upload APK
        uses: actions/upload-artifact@v3
        with:
          name: app-debug
          path: app/build/outputs/apk/debug/app-debug.apk
```

3. Push到GitHub后，在Actions标签页下载生成的APK

#### 选项B：使用App Center或类似服务

1. 注册 App Center (https://appcenter.ms/)
2. 上传项目代码
3. 配置构建并生成APK

## 安装和测试

1. 将生成的APK文件传输到Android手机
2. 在手机上启用"未知来源"安装权限
3. 点击APK文件进行安装
4. 安装完成后，打开"个人名片"应用

## 使用说明

1. **启动应用** - 看到欢迎界面
2. **点击"开始创建名片"按钮** - 进入信息输入页面
3. **填写所有信息**:
   - 姓名
   - 学号
   - 专业
   - 联系电话
   - 电子邮箱
4. **点击"提交信息"按钮** - 查看生成的名片
5. **点击"返回首页"按钮** - 返回欢迎界面

## 注意事项

1. 所有输入字段都是必填项，不填写完整会提示错误
2. 应用不保存数据，关闭后需要重新输入
3. 适用于Android 5.0及以上版本的设备

## 截图建议

为了完成课程作业，建议截取以下界面：
1. 欢迎页面
2. 信息输入页面（填写信息后）
3. 信息展示页面（显示完整名片）

## 课设报告建议

可以包含以下内容：
1. **项目概述** - 介绍应用功能和目的
2. **技术实现** - 说明使用的Android组件（Activity、Intent、Layout等）
3. **界面设计** - 展示UI布局和设计思路
4. **功能流程** - 说明页面跳转和数据传递
5. **测试结果** - 提供运行截图
6. **总结** - 学习收获和改进建议

## 可能的扩展功能（选做）

1. 数据持久化（使用SharedPreferences或数据库）
2. 添加头像上传功能
3. 名片分享功能
4. 多名片管理
5. 更美观的UI设计

## 常见问题

**Q: 为什么需要允许"未知来源"安装？**
A: 因为这是自己编译的APK，不是从应用商店下载的，需要手动允许安装。

**Q: 可以修改应用名称吗？**
A: 可以，修改 `res/values/strings.xml` 中的 `app_name` 即可。

**Q: 如何修改应用图标？**
A: 需要准备不同尺寸的图标放入mipmap文件夹，或使用Android Studio的Image Asset功能生成。

## 作者信息

- 项目名称: 个人名片
- 版本: 1.0
- 创建日期: 2025年

## 许可证

本项目仅用于学习和教学目的。
# Android Project
