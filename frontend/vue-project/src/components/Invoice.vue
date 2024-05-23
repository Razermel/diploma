<template>
  <div>
    <div v-if="isLoading">
      <p>Loading...</p>
    </div>
    <div v-else>
      <h1 v-if="invoice">Invoice</h1>
      <div v-if="invoice">
        <p>ID: {{ invoice.id }}</p>
        <p>Total Cost: {{ invoice.totalCost }}</p>
        <p>Supplier: {{ invoice.supplier }}</p>
        <p>Customer: {{ invoice.customer }}</p>
        
        <h2>Products</h2>
        <table class="product-table">
          <thead>
            <tr>
              <th>Product</th>
              <th>Price</th>
              <th>Weight</th>
              <th>Article</th>
              <th>Count</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(product, index) in invoice.invoiceProducts" :key="index">
              <td>{{ product.product.name }}</td>
              <td>{{ product.product.price }}</td>
              <td>{{ product.product.weight }}</td>
              <td>{{ product.product.article }}</td>
              <td>{{ product.count }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios';
import { ref, watch } from 'vue';

const isLoading = ref(true);
const invoice = ref(null);

const fetchInvoice = async (id, token) => {
  try {
    const response = await axios.get(`http://localhost:8080/api/v1/invoices/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    invoice.value = response.data;
    console.log('Счета-фактура успешно загружена:', response.data);
  } catch (error) {
    console.error('Ошибка при загрузке счета-фактуры', error);
  } finally {
    isLoading.value = false;
  }
};

watch(invoice, (newValue) => {
  if (newValue) {
    isLoading.value = false;
  }
});

const id = 1;
const token = localStorage.getItem('token');
fetchInvoice(id, token);
</script>

<style>
.product-table th,
.product-table td {
  padding: 0.5rem;
}
</style>