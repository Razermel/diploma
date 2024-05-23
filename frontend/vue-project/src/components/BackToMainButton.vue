<!-- src/components/BackToMainButton.vue -->
<template>
    <button class="back-button" @click="navigateToMain">Главная</button>
  </template>
  
  <script setup>
  import { useRouter } from 'vue-router';
  
  const router = useRouter();
  
  const navigateToMain = () => {
    router.push('/main');
  };
  </script>
  
  <style scoped>
  .back-button {
    position: absolute;
    top: 20px;
    left: 20px;
    padding: 10px 20px;
    background: #007bff;
    color: #fff;
    border: none;
    border-radius: 5px;
    cursor: pointer;
    font-size: 16px;
  }
  
  .back-button:hover {
    background: #0056b3;
  }
  
  .back-button:active {
    background: #004494;
  }
  </style>
  