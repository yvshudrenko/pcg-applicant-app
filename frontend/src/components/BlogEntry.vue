<script setup lang="ts">
import { ref } from 'vue'

defineProps({
  msg: String
})

const count = ref(0);
</script>

<template>
    <v-container fluid>
        <v-row>
          <v-col>
            <v-img
              :width="300"
              aspect-ratio="16/9"
              cover
              src="https://cdn.vuetifyjs.com/images/parallax/material.jpg"
            ></v-img>
            <v-text-field
                label="Altes Passwort"
            ></v-text-field>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
            text here
          </v-col>
        </v-row>
    </v-container>
</template>

<style scoped>
.read-the-docs {
  color: #888;
}
</style>
