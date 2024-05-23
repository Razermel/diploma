<script setup>
import { useRouter } from 'vue-router'
import { ref, defineProps } from 'vue'
import { togglebutton } from './Main.vue'

const showLogoutButton = ref(false)
const router = useRouter()
const role = ref('Студент')

function back2() {
  router.push('/main')
}

function toggleLogoutButton() {
  showLogoutButton.value = !showLogoutButton.value
}

function logout() {
  router.push('/')
}

function changerole() {
  role.value = role.value === 'Студент' ? 'Преподаватель' : 'Студент'
}
</script>

<template>
  <div class="">
    <header class="flex justify-between ml-8 mr-8 mt-5 mb-5">
      <div class="flex items-center">
        <h2
          @click="back2"
          class="text-xl font-bold cursor-pointer transition-all text-cyan-950 hover:text-cyan-600"
        >
          GitCourse
        </h2>
      </div>

      <div class="flex items-center">
        <p class="user" @click="toggleLogoutButton">pa3etka</p>
        <p class="">:</p>
        <p class="ml-4">{{ role }}</p>
        <button v-if="showLogoutButton" @click="togglebutton(), changerole()" class="change-button">
          Сменить роль
        </button>
        <button v-if="showLogoutButton" @click="logout" class="logout-button">Выйти</button>
      </div>
    </header>
  </div>
</template>

<style skoped>
.user {
  cursor: pointer;
  font-weight: 500;
  transition: font-weight 0.1s ease;
}
.user:hover {
  font-weight: bold;
}

.logout-button {
  background-color: #dc2626;
  color: #ffffff;
  border-radius: 0.75rem;
  margin-left: 1rem;
  padding-left: 0.75rem;
  padding-right: 0.75rem;
  padding-top: 0.1rem;
  padding-bottom: 0.25rem;
  transition:
    height 1s ease,
    opacity 1s ease;
}

.change-button {
  background-color: rgb(14 116 144);
  color: #ffffff;
  border-radius: 0.75rem;
  margin-left: 1rem;
  padding-left: 0.75rem;
  padding-right: 0.75rem;
  padding-top: 0.1rem;
  padding-bottom: 0.25rem;
  transition:
    height 1s ease,
    opacity 1s ease;
}

.logout-button:hover {
  background-color: #bc1f1f;
}

.logout-button:active {
  background-color: #9e1b1b;
}

.change-button:hover {
  background-color: rgb(21 94 117);
}

.change-button:active {
  background-color: rgb(22 78 99);
}

.show {
  opacity: 1;
  height: auto;
}
</style>
