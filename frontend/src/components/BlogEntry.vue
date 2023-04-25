<script setup lang="ts">

const props = defineProps({
  description: String,
  imageUrl: String,
  isLiked: Boolean
})

</script>

<template>



  <v-card
      class="mx-auto"
      max-width="450"
    >
    <v-img
      :src="imageUrl"
      cover
      :aspect-ratio="16/9"
    >
    </v-img>
    <v-card-title class="flex-column align-start">
      <div class="text-h4 mb-2">
        title
      </div>
      <div class="text-h6 font-weight-regular text-grey">
        {{ description }}
      </div>
      <div class="d-flex align-center">
        <!-- Task -->
        <!-- <v-avatar
          size="24"
          class="me-4"
        >
          <v-img
            :src="imageUrl"
            contain
          ></v-img>
        </v-avatar> -->

        <!-- <span class="text-body-2 text-grey">81° / 62°</span> -->
      </div>
    </v-card-title>

    <v-divider class="mx-4"></v-divider>

    <v-card-text class="d-flex justify-space-between">
      <v-icon icon="mdi-heart clickable" v-if="isLiked" @click="$emit('triggerLike')"></v-icon>
      <v-icon icon="mdi-heart-outline clickable" v-else @click="$emit('triggerLike')"></v-icon>
    </v-card-text>
  </v-card>


    <!-- <v-container fluid>
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
          </v-col>
        </v-row>
    </v-container> -->


</template>

<style scoped>
.read-the-docs {
  color: #888;
}
</style>
