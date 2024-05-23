<template>
  <div class="main-wrapper">
    <div class="main-content">
      <h1>Добро пожаловать в систему управления счетами-фактурами</h1>
      <p>
        Этот проект позволяет управлять счетами-фактурами, создавать новые заказы и просматривать существующие.
        Вы можете добавлять информацию о поставщиках, покупателях и товарах в каждом счете-фактуре.
      </p>
      <div class="button-group">
        <button class="btn" @click="navigateToCreateInvoice">Создать счет-фактуру</button>
        <button class="btn" @click="navigateToInvoices">Посмотреть счета-фактуры</button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';

const router = useRouter();

const navigateToCreateInvoice = () => {
  router.push('/create');
};

const navigateToInvoices = () => {
  router.push('/invoices');
};
</script>

<style scoped>
@import url('https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800;900&display=swap');

html, body {
  height: 100%;
  margin: 0;
  font-family: 'Poppins', sans-serif;
}

body {
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: #f0f4f8;
}

.main-wrapper {
  margin-top: 10%;
  width: 100%;
  padding: 40px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  text-align: center;
}

h1 {
  font-size: 28px;
  margin-bottom: 20px;
}

p {
  font-size: 18px;
  margin-bottom: 30px;
}

.button-group {
  display: flex;
  justify-content: space-around;
}

.btn {
  padding: 10px 20px;
  background: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  font-size: 16px;
}

.btn:hover {
  background: #0056b3;
}

.btn:active {
  background: #004494;
}
</style>
