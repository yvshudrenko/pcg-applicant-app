<template>
    <div class="layout">
      <div class="sidebar">
        <v-toolbar :color="'#4c70a2'">
          <h2 class="bar_title">Beitrag posten</h2>
        </v-toolbar>
        <div class="input_area">
          <v-textarea label="Storie" auto-grow variant="solo"></v-textarea>
        </div>
        <v-btn :color="'blue'" class="m8">Posten</v-btn>
      </div>
      <div class="content">
        <v-toolbar :color="'#4c70a2'">
          <v-text-field
            v-model="searchField"
            clearable
            hide-details
            prepend-inner-icon="mdi-magnify"
            label="Enter to search.."
            variant="solo"
            single-line
          ></v-text-field>
        </v-toolbar>
        <v-row class="pa-10">
            <v-col v-for="blog in blogEntriesFiltered" cols=3 class="m-0">
              <BlogEntry :description="blog.description" :image-url="blog.imageUrl" :is-liked="blog.isLiked" @triggerLike="blog.isLiked = !blog.isLiked"/>
            </v-col>
        </v-row>
      </div>
    </div>
</template>

<script setup lang="ts">
  import BlogEntry from './components/BlogEntry.vue';
  import { ref, reactive, computed } from 'vue';
  
  const blogExample = reactive(
    {
      title: 'Dolor',
      description: 'blablabla',
      imageUrl: "https://cdn.vuetifyjs.com/images/parallax/material.jpg",
      isLiked: false
    }
  );

  const blogEntriesFiltered = computed( () => blogEntries.filter(b => !searchField.value.length || b.description.includes(searchField.value)));

  const searchField = ref(''); 

  // Task
  // function isFiltered(blog: any) {
  //   return !searchField.value.length || blog.description.includes(searchField.value);
  // }

  const blogEntries = reactive([1, 2, 3, 4, 5, 6].map(i => structuredClone({...blogExample, description: blogExample.description + ' ' + i})));
  
</script>

<style scoped>
.layout {
  flex:1;
  display: flex;
  flex-direction: row;
  background-color: hsl(250,100%,99%);
}
.sidebar {
  width:280px;
  flex-direction: column;
  background-color: #aff;
  box-shadow: 0px 0px 7px 0px rgba(0, 0, 0, 0.19);
  display: flex;
  position: relative;
}
.content{
  flex: 1;
  position: relative;
}
.input_area{
  min-height:80px;
  margin:22px 8px 8px;
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
