# record-app
タスク管理ができるカレンダー

### 技術スタック

#### backend

- java
- Spring Boot
- Spring Security (認証/認可)
- MyBatis (ORM)
- MySQL
- Junit (testing framework)
- DBRider (testing framework)
- Docker 

#### frontend
- Vue.js
- Vuex
- vue-router
- Vuetify (css)

---

### 動作確認手順(local/docker)
 - `./gradlew dockerBuildImage` を実行しdocker imageを作成
 - `local/`ディレクトリ内でdocker-composeを実行
 - `docker-compose up`

### フロントビルド手順
 - 下記コマンドで静的リソースを作成
 - `npm run build`

