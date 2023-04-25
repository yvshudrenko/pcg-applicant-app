<script setup lang="ts">
  import BlogEntry from './components/BlogEntry.vue';
</script>

<template>
    <div class="layout">
      <div class="sidebar">Sidebar</div>
      <v-col cols="10">
        Content
        <BlogEntry />
      </v-col>
    </div>
</template>

<style scoped>
.layout {
  flex:1;
  display: flex;
  flex-direction: row;
}
.sidebar {
  width:70rem;
  flex:1;
  box-shadow: 0px 0px 8px 0px rgba(0, 0, 0, 0.19);
  display: flex;
}
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
