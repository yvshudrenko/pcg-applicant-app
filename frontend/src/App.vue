<template>
    <div class="layout">
      <div class="sidebar">
        <v-toolbar :color="'#4c70a2'">
          <h2 class="bar_title">Public Chat Group</h2>
        </v-toolbar>
        <SidebarContent :baseUrl="baseUrl" :onPost="initDataFromBackend"/>
      </div>
      <div class="content">
        <div class="toolbar_header">
        <v-toolbar :color="'#4c70a2'">
          <v-text-field
            v-model="searchField"
            class="ma-5"
            clearable
            hide-details
            prepend-inner-icon="mdi-magnify"
            label="Enter to search.."
            variant="solo"
            single-line
          ></v-text-field>
          </v-toolbar>
        </div>
        <div class="scrollview">
          <v-row class="pa-10">
            <v-col v-for="blog in blogEntriesFiltered" :key="blog.id" cols=3 class="m-0">
              <BlogEntry :blog="blog" @triggerLike="blog.isLiked = !blog.isLiked" @deletePost="deletePost"/>
            </v-col>
          </v-row>
        </div>
      </div>
    </div>
</template>

<script setup lang="ts">
  import BlogEntry from './components/BlogEntry.vue';
  import SidebarContent from './components/SidebarContent.vue';
  import { ref, reactive, computed, onMounted } from 'vue';
  
  export type Blog = {
    id: number;
    createdAt: number;
    title: string;
    content: string;
    isLiked: boolean;
    pictures: any[];
  };

  const searchField = ref(''); 
  const baseUrl = "http://192.168.1.35:8080";

  const blogEntries: Blog[] = reactive([]);
  const blogEntriesFiltered = computed( () => blogEntries.filter(b => !searchField.value.length || b.content.includes(searchField.value)));
  const blogExample: Blog = reactive(
    {
      id: 0,
      title: 'Dolor',
      content: 'blablabla',
      isLiked: false,
      pictures: [],
      createdAt: new Date().getTime()
    }
  );

  function initDummyBlogList()
  {
    [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14].forEach(i => blogEntries.push( structuredClone({...blogExample, content: blogExample.content + ' ' + i, id: i}) ))
  }

  onMounted( async () => {
    await initDataFromBackend()
  });

  // Task
  // function isFiltered(blog: any) {
  //   return !searchField.value.length || blog.description.includes(searchField.value);
  // }

  async function getMessages() {
    const response = await fetch(baseUrl + '/messages');
    return await response.json();
  }
  async function initDataFromBackend(){
    let resp: any[] = await getMessages();

    if (!resp.length)
    {
      initDummyBlogList();
      console.log(`initialized dummy blog list: `, blogEntries);
    }
    else
    {
      blogEntries.splice(0,blogEntries.length);

      resp.forEach( (it, index) => blogEntries.push( {...it, content: it.content + ' ' + index, title: 'Sample message #' + index, isLiked: false} ));
      
      blogEntries.sort(function(a, b) {
        if (a.createdAt < b.createdAt) return -1;
        if (a.createdAt > b.createdAt) return 1;
        return 0;
      });
    
      console.log(`initialized blog list from backend data: `, blogEntries);
    }
  }
  function deletePost(_: any, blogId: Number) {
    blogEntries.splice(blogEntries.findIndex((blog: Blog) => blog.id === blogId), 1);
  }
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
.sidebar_content{
  flex-direction: column;
  display: flex;
  padding: 12px;
  gap: 8px;
}
.toolbar_header{
  height:64px;
}
.content{
  flex: 1;
  flex-direction: column;
  display: flex;
  position: relative;
}
.input_area{
  flex:1;
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
.scrollview {
  flex:1;
  overflow-x: hidden;
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
