<template>
  <div class="wrapper">
    <BackToMainButton />
    <h1>Создать заказ</h1>
    <form @submit.prevent="createInvoice">
      <h2>Поставщик</h2>
      <div class="input-box">
        <label for="supplierName">Название:</label>
        <input type="text" id="supplierName" v-model="supplier.name" required>
      </div>

      <div class="input-box">
        <label for="supplierAddress">Адрес:</label>
        <input type="text" id="supplierAddress" v-model="supplier.address" required>
      </div>

      <div class="input-box">
        <label for="supplierPhone">Телефон:</label>
        <input type="text" id="supplierPhone" v-model="supplier.phone" required>
      </div>

      <h2>Покупатель</h2>
      <div class="input-box">
        <label for="customerName">Название:</label>
        <input type="text" id="customerName" v-model="customer.name" required>
      </div>

      <div class="input-box">
        <label for="customerAddress">Адрес:</label>
        <input type="text" id="customerAddress" v-model="customer.address" required>
      </div>

      <div class="input-box">
        <label for="customerPhone">Телефон:</label>
        <input type="text" id="customerPhone" v-model="customer.phone" required>
      </div>

      <div v-for="(product, index) in products" :key="index" class="product-box">
        <h2>Товар {{ index + 1 }}</h2>
        <div class="input-box">
          <label for="productName">Название:</label>
          <input type="text" id="productName" v-model="product.name" required>
        </div>

        <div class="input-box">
          <label for="productPrice">Цена:</label>
          <input type="number" id="productPrice" v-model="product.price" required>
        </div>

        <div class="input-box">
          <label for="productWeight">Вес:</label>
          <input type="number" id="productWeight" v-model="product.weight" required>
        </div>

        <div class="input-box">
          <label for="productArticle">Артикул:</label>
          <input type="text" id="productArticle" v-model="product.article" required>
        </div>

        <div class="input-box">
          <label for="productCount">Количество:</label>
          <input type="number" id="productCount" v-model="product.count" required>
        </div>

        <button type="button" class="btn remove-btn" @click="removeProduct(index)">Удалить товар</button>
      </div>

      <button type="button" class="btn add-btn" @click="addProduct">Добавить товар</button>

      <button type="submit" class="btn submit-btn">Создать счет-фактуру</button>
    </form>
    <button class="btn navigate-btn" @click="navigateToInvoices">Перейти к счетам-фактурам</button>
  </div>
</template>

<script setup>
import axios from 'axios';
import { ref } from 'vue';
import { useRouter } from 'vue-router';
import { useToast } from "vue-toastification";
import BackToMainButton from '@/components/BackToMainButton.vue';

const supplier = ref({ name: '', address: '', phone: '' });
const customer = ref({ name: '', address: '', phone: '' });
const products = ref([
  { name: '', price: null, weight: null, article: '', count: null },
]);

const router = useRouter();
const toast = useToast();

const addProduct = () => {
  products.value.push({ name: '', price: null, weight: null, article: '', count: null });
};

const removeProduct = (index) => {
  products.value.splice(index, 1);
};

const createInvoice = async () => {
  const token = localStorage.getItem('token');

  // Формируем данные в JSON формате
  const invoiceData = {
    supplier: supplier.value,
    customer: customer.value,
    invoice: {
      totalCost: 0,
      status: false
    },
    invoiceProducts: products.value.map(product => ({
      product: {
        name: product.name,
        price: product.price,
        weight: product.weight,
        article: product.article
      },
      count: product.count
    }))
  };

  try {
    const response = await axios.post('http://localhost:8080/api/v1/invoices/create', invoiceData, {
      headers: {
        Authorization: `Bearer ${token}`,
        'Content-Type': 'application/json'
      },
    });
    console.log('Invoice created:', response.data);
    toast.success("Счет-фактура успешно создана!");
    supplier.value = { name: '', address: '', phone: '' };
    customer.value = { name: '', address: '', phone: '' };
    products.value = [{ name: '', price: null, weight: null, article: '', count: null }];
  } catch (error) {
    console.error('Error creating invoice:', error);
    toast.error("Ошибка при создании счет-фактуры.");
  }
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

.wrapper {
  background: #fff;
  margin-left: 10%;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 80%;
}

h1 {
  font-size: 24px;
  text-align: center;
  margin-bottom: 20px;
}

h2 {
  font-size: 20px;
  margin-top: 20px;
}

.input-box {
  margin-bottom: 15px;
}

.input-box label {
  display: block;
  margin-bottom: 5px;
  font-weight: 500;
}

.input-box input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
}

.product-box {
  border: 1px solid #ddd;
  padding: 10px;
  border-radius: 5px;
  margin-bottom: 20px;
}

.btn {
  display: inline-block;
  padding: 10px 20px;
  background: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
  text-align: center;
  margin-top: 10px;
}

.remove-btn {
  background: #dc3545;
}

.add-btn {
  display: block;
  width: 100%;
  margin-top: 20px;
  background: #28a745;
}

.submit-btn {
  display: block;
  width: 100%;
  margin-top: 20px;
  background: #007bff;
}

.navigate-btn {
  display: block;
  width: 100%;
  margin-top: 20px;
  background: #ff077b;
}
</style>
