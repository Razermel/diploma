<template>
  <div class="wrapper">
    <BackToMainButton />
    <div class="content">
      <h1>Счета-фактуры</h1>
      <div v-if="isLoading" class="loading">Загрузка...</div>
      <div v-else>
        <div v-for="invoice in invoices" :key="invoice.id" class="invoice">
          <div class="invoice-header">
            <p><strong>ID:</strong> {{ invoice.id }}</p>
            <p><strong>Название Поставщика:</strong> {{ invoice.supplier.name }}</p>
            <p><strong>Название Покупателя:</strong> {{ invoice.customer.name }}</p>
            <p><strong>Общая стоимость</strong> {{ invoice.totalCost }}</p>
            <p><strong>Статус:</strong> {{ getStatusText(invoice.status)  }}</p>
            <input type="checkbox" :checked="invoice.status" @change="updateInvoiceStatus(invoice, $event.target.checked)">
          </div>
          <div class="products">
            <h3>Товары</h3>
            <table class="product-table">
              <thead>
                <tr>
                  <th>Название</th>
                  <th>Цена</th>
                  <th>Вес</th>
                  <th>Артикул</th>
                  <th>Количество</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(product, productIndex) in invoice.invoiceProducts" :key="productIndex">
                  <td>{{ product.product.name }}</td>
                  <td>{{ product.product.price }}</td>
                  <td>{{ product.product.weight }}</td>
                  <td>{{ product.product.article }}</td>
                  <td>{{ product.count }}</td>
                </tr>
                 <tr v-if="!invoice.invoiceProducts || invoice.invoiceProducts.length === 0">
                <td colspan="5">No products found</td>
              </tr>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { ref, onMounted } from 'vue';
import BackToMainButton from '@/components/BackToMainButton.vue'; // Импортируем кнопку

const isLoading = ref(true);
const invoices = ref([]);

const fetchInvoices = async (token) => {
  try {
    const response = await axios.get('http://localhost:8080/api/v1/invoices', {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    console.log('Fetched invoices:', response.data); // Log the entire response data
    invoices.value = response.data.map(invoice => ({
      ...invoice,
      products: invoice.products || [] // Ensure products is an array
    }));
    console.log('Processed invoices:', invoices.value); // Log the processed invoices
  } catch (error) {
    console.error('Ошибка при загрузке счетов-фактур', error);
  } finally {
    isLoading.value = false;
  }
};

const updateInvoiceStatus = async (invoice, newStatus) => {
  const previousStatus = invoice.status; // Save the previous status in case of an error
  invoice.status = newStatus; // Optimistically update the status

  try {
    const response = await axios.put(
      `http://localhost:8080/api/v1/invoices/${invoice.id}/status`,
      { status: newStatus },
      {
        headers: {
          Authorization: `Bearer ${localStorage.getItem('token')}`,
        },
      }
    );
    console.log('Статус счета-фактуры успешно обновлен:', response.data);
  } catch (error) {
    console.error('Ошибка при обновлении статуса счета-фактуры', error);
    invoice.status = previousStatus; // Revert the status update in case of an error
  }
};

const getStatusText = (status) => {
  return status ? 'Активный' : 'Неактивный'; // Пример текстовых описаний
};

onMounted(() => {
  const token = localStorage.getItem('token');
  fetchInvoices(token);
});
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
  width: 80%;
  margin-left: 10%;
  margin-top: 20px;
  padding: 20px;
  background: #fff;
  border-radius: 10px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

.content {
  width: 100%;
}

h1 {
  font-size: 24px;
  text-align: center;
  margin-bottom: 20px;
}

.loading {
  font-size: 20px;
  font-weight: 600;
  text-align: center;
}

.invoice {
  margin-bottom: 20px;
}

.invoice-header {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #f9f9f9;
  margin-bottom: 10px;
}

.invoice-header p {
  margin: 5px 0;
}

.products {
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  background-color: #fff;
}

.product-table {
  width: 100%;
  border-collapse: collapse;
  margin-top: 10px;
}

.product-table th, .product-table td {
  padding: 10px;
  border: 1px solid #ddd;
  text-align: left;
}

.product-table th {
  background-color: #f2f2f2;
  font-weight: 600;
}

.btn {
  padding: 5px 10px;
  margin: 5px;
  background: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.btn:hover {
  background-color: #0056b3;
}

.btn:active {
  background-color: #004494;
}
</style>
