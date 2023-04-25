<template>
    <div class="layout">
      <div class="sidebar">
        <v-toolbar :color="'#4c70a2'">
          <h2 class="bar_title">Beitrag posten</h2>
        </v-toolbar>
        <div class="input_area">
          <v-textarea label="Storie" auto-grow variant="solo"></v-textarea>
        </div>
        <v-btn class="m8">Posten</v-btn>
      </div>

      <v-row class="pa-10">
          <v-col v-for="blog in blogEntries" cols=3 class="m-0">
            <BlogEntry :image-url="blog.imageUrl" :is-liked="blog.isLiked" @triggerLike="blog.isLiked = !blog.isLiked"/>
          </v-col>
      </v-row>
  
    </div>
</template>

<script setup lang="ts">
  import BlogEntry from './components/BlogEntry.vue';
  import { reactive, toRaw } from 'vue';
  
  const blogExample = reactive(
    {
      description: "blabla",
      imageUrl: "https://cdn.vuetifyjs.com/images/parallax/material.jpg",
      isLiked: false
    }
  );

  const blogEntries = reactive([1, 2, 3, 4, 5, 6].map(_ => structuredClone(toRaw(blogExample))));
  
</script>

<style scoped>
.layout {
  flex:1;
  display: flex;
  flex-direction: row;
  background-color: hsl(250,100%,99%);
}
.sidebar {
  flex:1;
  width:70rem;
  flex-direction: column;
  background-color: #fff;
  box-shadow: 0px 0px 8px 0px rgba(0, 0, 0, 0.19);
  display: flex;
}
.input_area{
  min-height:80px;
  margin:8px;
  position: relative;
}
.bar_title {
  padding-left: 12px;
  font-size: 20px;
  font-weight: 600;
  color:#fff;
}
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.m8 {
  margin:8px;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
