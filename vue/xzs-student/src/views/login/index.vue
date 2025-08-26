<template>
  <div class="login-view">
    <!-- 全屏背景 -->
    <div class="bg"></div>

    <!-- 居中卡片 -->
    <div class="login-card">
      <div class="brand">
        <img class="logo" src="@/assets/logo2.png" alt="logo">
        <h1 class="title">基础理论学习</h1>
      </div>

      <el-form ref="form" :model="form" :rules="rules" label-position="top" class="form">
        <el-form-item label="用户名" prop="userName">
          <el-input v-model="form.userName" placeholder="请输入用户名" prefix-icon="el-icon-user" />
        </el-form-item>

        <el-form-item label="密码" prop="password">
          <el-input v-model="form.password" type="password" placeholder="请输入密码" show-password prefix-icon="el-icon-lock" />
        </el-form-item>

        <el-button type="primary" class="btn-login" :loading="submitting" @click="onSubmit">登 录</el-button>

        <div class="extra">
          <span class="link" @click="$router.push('/register')">立即注册</span>
          <span class="link muted">忘记密码？</span>
        </div>
      </el-form>
    </div>

    <footer class="copyright">© CETC-38</footer>
  </div>
</template>

<script>
// 只沿用原有接口，不改名不改路径
import loginApi from '@/api/login'

export default {
  name: 'Login',
  data () {
    return {
      submitting: false,
      form: {
        userName: '',
        password: ''
      },
      rules: {
        userName: [{ required: true, message: '请输入用户名', trigger: 'blur' }],
        password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
      }
    }
  },
  methods: {
    onSubmit () {
      this.$refs.form.validate(async valid => {
        if (!valid) return
        try {
          this.submitting = true
          // 与项目原有保持一致：/api/user/login
          const res = await loginApi.login({
            userName: this.form.userName,
            password: this.form.password
          })
          // 按项目约定：code===200 表示成功；cookie 在后端下发
          if (res && (res.code === 1 || res.code === 200 || res.success)) {
            this.$message.success('登录成功')
            this.$router.push('index')
          } else {
            this.$message.error(res && res.message ? res.message : '登录失败')
          }
        } catch (e) {
          // request.js 已经做了错误处理，这里保持安静或轻提示
        } finally {
          this.submitting = false
        }
      })
    }
  }
}
</script>

<style lang="scss" scoped>
.login-view {
  position: relative;
  min-height: 100vh;
  display: grid;
  place-items: center;
  overflow: hidden;
}

/* 全屏背景：渐变 + 大图 */
.bg {
  position: fixed;
  inset: 0;
  background:
    linear-gradient(180deg, rgba(0, 96, 190, .45), rgba(0, 96, 190, .25)),
    url('~@/assets/login-bg.jpg') center/cover no-repeat;
  filter: saturate(1.05);
  z-index: -1;
}

/* 登录卡片 */
.login-card {
  width: 420px;
  padding: 28px 28px 22px;
  background: rgba(255, 255, 255, .96);
  border-radius: 16px;
  box-shadow: 0 30px 70px rgba(0, 0, 0, .25);
  backdrop-filter: blur(2px);
}

.brand {
  text-align: center;
  margin-bottom: 10px;
  .logo {
    width: 72px;
    height: 72px;
    object-fit: contain;
    margin-bottom: 8px;
  }
  .title {
    margin: 0;
    font-size: 24px;
    font-weight: 700;
    letter-spacing: .02em;
    color: #222;
  }
}

.form {
  margin-top: 8px;
  ::v-deep .el-form-item__label {
    color: #5e6d82;
    padding-bottom: 4px;
  }
  ::v-deep .el-input__inner {
    height: 40px;
    line-height: 40px;
    border-radius: 8px;
  }
}

.btn-login {
  width: 100%;
  height: 42px;
  margin-top: 6px;
  border-radius: 10px;
  font-weight: 600;
}

/* 底部版权 */
.copyright {
  position: fixed;
  left: 0; right: 0; bottom: 10px;
  text-align: center;
  color: #fff;
  font-size: 13px;
  text-shadow: 0 1px 2px rgba(0,0,0,.3);
  pointer-events: none;
}

/* 右下角链接区 */
.extra {
  display: flex;
  justify-content: space-between;
  margin-top: 10px;
  .link {
    font-size: 13px;
    cursor: pointer;
    user-select: none;
    color: #2979ff;
  }
  .muted { color: #8aa0c8; cursor: default; }
}

/* 响应式 */
@media (max-width: 480px) {
  .login-card { width: 92vw; padding: 22px; }
}
</style>
