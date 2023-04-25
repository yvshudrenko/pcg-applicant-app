<script setup lang="ts">

const props = defineProps({
  description: String,
  imageUrl: String,
  isLiked: Boolean
})

</script>

<template>
    <v-container fluid>
        <v-row>
          <v-col>
            <v-img
              aspect-ratio="16/9"
              cover
              :src=imageUrl
            ></v-img>
            <div class="mt-2 d-inline-flex justify-end border w-100">
              <v-icon icon="mdi-heart clickable" v-if="isLiked" @click="$emit('triggerLike')"></v-icon>
              <v-icon icon="mdi-heart-outline clickable" v-else @click="$emit('triggerLike')"></v-icon>
            </div>
            <div class="mt-2">{{ description }}</div>
            <!-- <div class="mt-2">
              Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
            </div> -->
          </v-col>
        </v-row>
    </v-container>
</template>

<style scoped>
.read-the-docs {
  color: #888;
}
</style>
