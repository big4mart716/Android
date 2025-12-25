# 快速获取APK文件指南

## 最简单的方法：使用GitHub Actions在线构建

由于本机没有Android开发环境，推荐使用GitHub的免费在线构建服务：

### 步骤：

#### 1. 准备GitHub账号
- 如果没有GitHub账号，先到 https://github.com 注册一个（免费）

#### 2. 上传项目到GitHub

**方法A：使用GitHub网页（最简单）**
1. 登录GitHub，点击右上角的"+"，选择"New repository"
2. 输入仓库名称（如: personal-card-app）
3. 选择Public，点击"Create repository"
4. 在新页面点击"uploading an existing file"
5. 将PersonalCard文件夹内的所有文件拖拽上传
6. 点击"Commit changes"

**方法B：使用命令行（如果熟悉git）**
```bash
cd ~/temp/PersonalCard
git init
git add .
git commit -m "Initial commit"
git branch -M main
git remote add origin https://github.com/你的用户名/personal-card-app.git
git push -u origin main
```

#### 3. 自动构建APK
- 文件上传完成后，GitHub会自动开始构建
- 点击仓库页面上方的"Actions"标签
- 等待构建完成（通常1-3分钟，显示绿色对勾✓）

#### 4. 下载APK
- 在Actions页面，点击最新的workflow运行记录
- 向下滚动到"Artifacts"部分
- 点击"PersonalCard-debug"下载APK文件
- 解压下载的zip文件，得到app-debug.apk

#### 5. 安装到手机
1. 将app-debug.apk文件传输到Android手机
   - 可以通过USB传输
   - 或者通过微信/QQ发送给自己
   - 或者通过云盘下载
2. 在手机上找到APK文件并点击安装
3. 如果提示"不允许安装未知来源"，需要在设置中允许

---

## 备选方案：借用有环境的电脑

如果GitHub方法不熟悉，可以：

### 方案1：在学校机房/实验室
- 学校的计算机实验室通常有Android Studio
- 带U盘拷贝项目文件夹
- 使用Android Studio打开项目并构建

### 方案2：借用同学的电脑
- 找一个安装了Android Studio的同学
- 让他帮忙打开项目
- Build > Build Bundle(s) / APK(s) > Build APK(s)
- 生成的APK在: app/build/outputs/apk/debug/app-debug.apk

### 方案3：在线Android IDE
使用在线开发环境（可能需要注册）：
- Replit (https://replit.com) - 支持Android开发
- Codenvy / Eclipse Che

---

## 推荐：GitHub Actions方法最简单！

对于没有本地环境的情况，GitHub Actions是最佳选择：
- ✅ 完全免费
- ✅ 不需要安装任何软件
- ✅ 自动构建，不会出错
- ✅ 可以反复使用
- ✅ 保留30天，随时下载

---

## 需要帮助？

如果在使用GitHub Actions时遇到问题，检查：
1. workflow文件是否在 `.github/workflows/build.yml` 位置
2. 所有项目文件是否都已上传
3. Actions功能是否启用（在仓库Settings > Actions中）

---

## 时间安排建议

- 上传到GitHub: 5分钟
- 自动构建: 2-3分钟
- 下载APK: 1分钟
- 传输到手机并安装: 2分钟

**总计：约10分钟即可获得可运行的APK！**
