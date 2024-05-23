<script setup>
import axios from 'axios'
</script>

<script>
export default {
  data() {
    return {
      loginData: {
        username: '',
        password: ''
      },
      registerData: {
        full_name: '',
        phone_number: '',
        username: '',
        email: '',
        password: '',
        role: 'EMPLOYER' // По умолчанию выбираем роль WORKER
      },
      token: '',
      activeForm: 'login' // Добавляем переменную activeForm и инициализируем её значением "login"
    }
  },
  methods: {
    toggleForm() {
      this.activeForm = this.activeForm === 'login' ? 'register' : 'login'
    },
    login() {
      axios
        .post('http://localhost:8080/api/v1/auth/authenticate', {
          username: this.loginData.username,
          password: this.loginData.password
        })
        .then(response => {
          if (response.status >= 200 && response.status < 300) {
            console.log('Вход выполнен успешно')
            this.token = response.data.token
            localStorage.setItem('token', this.token)
            this.$router.push('/main')
          } else {
            console.error('Ошибка при входе')
          }
        })
        .catch(error => {
          console.error('Ошибка при входе', error)
        })
    },
    register() {
      axios
        .post('http://localhost:8080/api/v1/auth/register', {
          full_name: this.registerData.full_name,
          phone_number: this.registerData.phone_number,
          username: this.registerData.username,
          email: this.registerData.email,
          password: this.registerData.password,
          role: this.registerData.role
        })
        .then(response => {
          if (response.status >= 200 && response.status < 300) {
            console.log('Регистрация прошла успешно')
            this.token = response.data.token;
            localStorage.setItem('token', this.token);
            this.$router.push('/main')
          } else {
            console.error('Ошибка при регистрации')
          }
        })
        .catch(error => {
          console.error('Ошибка при регистрации', error)
        })
    }
  }
}
</script>

<template>
  <body>
    <div class="wrapper">
      <transition name="form" mode="out-in">
        <form key="login-form" v-if="activeForm === 'login'" @submit.prevent="login">
          <h1>Вход</h1>
          <div class="input-box">
            <input type="text" placeholder="Логин" v-model="loginData.username" required />
            <i class="bx bxs-user"></i>
          </div>

          <div class="input-box">
            <input type="password" placeholder="Пароль" v-model="loginData.password" required />
            <i class="bx bxs-lock-alt"></i>
          </div>

          <button type="submit" class="btn">Войти</button>

          <div class="register-link">
            <p>
              Нет аккаунта?
              <a href="javascript:void(0);" @click="toggleForm">Зарегистрироваться</a>
            </p>
          </div>
        </form>

        <form key="register-form" v-else @submit.prevent="register">
          <h1>Регистрация</h1>

          <div class="input-box">
            <input type="text" placeholder="Имя" v-model="registerData.full_name" required />
            <i class="bx bxs-user"></i>
          </div>

          <div class="input-box">
            <input type="text" placeholder="Телефон" v-model="registerData.phone_number" required />
            <i class="bx bxs-phone"></i>
          </div>

          <div class="input-box">
            <input type="text" placeholder="Логин" v-model="registerData.username" required />
            <i class="bx bxs-user"></i>
          </div>

          <div class="input-box">
            <input type="email" placeholder="Почта" v-model="registerData.email" required />
            <i class="bx bxs-envelope"></i>
          </div>

          <div class="input-box">
            <input type="password" placeholder="Пароль" v-model="registerData.password" required />
            <i class="bx bxs-lock-alt"></i>
          </div>

          <div class="input-box">
            <select v-model="registerData.role" required>
              <option value="" disabled selected>Выберите роль</option>
              <option value="EMPLOYER">Работодатель</option>
              <option value="WORKER">Рабочий</option>
            </select>
            <i class="bx bxs-briefcase"></i>
          </div>

          <button type="submit" class="btn">Зарегистрироваться</button>

          <div class="register-link">
            <p>Есть аккаунт? <a href="javascript:void(0);" @click="toggleForm">Войти</a></p>
          </div>
        </form>
      </transition>
    </div>
  </body>
</template>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap');

* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
  font-family: 'Poppins', sans-serif;
}

body {
  background-color: #2a4472;
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  /* background: url(img.jpg) no-repeat; */
  background-size: cover;
  background-position: center;
}

.wrapper {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
}

form {
  width: 100%;
  max-width: 600px;
  background: transparent;
  border: 3px solid rgba(255, 255, 255, 0.2);
  backdrop-filter: blur(20px);
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.2);
  color: white;
  border-radius: 10px;
  padding: 30px 40px;
}

.form-enter-active,
.form-leave-active {
  transition: opacity 0.5s ease;
}

.form-enter,
.form-leave-to {
  opacity: 0;
}

.form-enter-to,
.form-leave {
  opacity: 1;
}

.wrapper h1 {
  font-size: 36px;
  text-align: center;
}

.wrapper .input-box {
  position: relative;
  width: 100%;
  height: 50px;
  margin: 30px 0;
}

.input-box input,
.input-box select {
  width: 100%;
  height: 100%;
  background: transparent;
  border: none;
  outline: none;
  border: 2px solid rgba(255, 255, 255, 0.2);
  border-radius: 40px;
  font-size: 16px;
  color: white;
  padding: 0 20px;
  -webkit-appearance: none;
  -moz-appearance: none;
  appearance: none;
}

.input-box select {
  color: white;
}

.input-box select option {
  background-color: #2a4472; /* Match the background color of the form */
  color: white; /* Ensure text color is white for visibility */
}

.input-box input:focus,
.input-box select:focus {
  border-color: #c4c4c4;
}

.input-box input::placeholder,
.input-box select::placeholder {
  color: rgb(186, 186, 186);
}

.input-box i {
  position: absolute;
  right: 20px;
  top: 50%;
  transform: translateY(-50%);
  font-size: 20px;
  pointer-events: none;
}

.wrapper .remember-forgot {
  display: flex;
  justify-content: space-between;
  font-size: 14.5px;
  margin: -15px 0 15px;
}

.remember-forgot label input {
  accent-color: #ffffff;
  margin-right: 5px;
}

.remember-forgot a {
  color: #ffffff;
  text-decoration: none;
}

.remember-forgot a:hover {
  text-decoration: underline;
}

.wrapper .btn {
  width: 100%;
  height: 45px;
  background: #fff;
  border: none;
  outline: none;
  border-radius: 40px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  cursor: pointer;
  font-size: 16px;
  color: #333;
  font-weight: 600;
}

.wrapper .register-link {
  font-size: 14.5px;
  text-align: center;
  margin: 20px 0 15px;
}

.register-link p a {
  color: #fff;
  text-decoration: none;
  font-weight: 600;
}

.register-link p a:hover {
  text-decoration: underline;
}

.btn:hover {
  background-color: #2a2a72;
  color: #fff;
  border: 2px solid #c4c4c4;
  /* transform: translateY(-1px); */
  transition: 0.3s;
}

.btn:active {
  background-color: #1e1e54;
  color: #fff;
  /* transform: translateY(-1px); */
  transition: 0.3s;
}
</style>
