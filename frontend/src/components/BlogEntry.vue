<script setup lang="ts">
import { ref } from 'vue'

defineProps({
  msg: String,
})

const count = ref(0)
</script>

<template>
    <v-container class="pt-0">
        <v-row>
          <v-col>
            picture here
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
