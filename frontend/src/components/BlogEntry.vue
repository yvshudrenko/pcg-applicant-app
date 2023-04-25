<script setup>
import { ref } from 'vue'

defineProps({
  msg: String,
})

const count = ref(0)
</script>

<template>
    <v-container class="pt-0">
        <v-row>
          <v-col>PCG app</v-col>
        </v-row>
    </v-container>
</template>

<style scoped>
.read-the-docs {
  color: #888;
}
</style>
