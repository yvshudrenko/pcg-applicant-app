<template>
    <div class="layout">
      <div class="sidebar">
        <v-toolbar :color="'#4c70a2'">
          <h2 class="bar_title">Public Chat Group</h2>
        </v-toolbar>
        <div class="sidebar_content">
          <v-text-field
            v-model="titleField"
            clearable
            hide-details
            label="Enter title.."
            variant="solo"
            single-line
            density="compact"
          ></v-text-field>
          <v-textarea
            v-model="storyField"
            label="Story" 
            auto-grow 
            variant="solo"
          ></v-textarea>
          <v-row>
            <v-col>
            <v-file-input
              label="Upload image"
              chips
              variant="solo"
              prepend-inner-icon="mdi-camera"
              density="compact"
            ></v-file-input>
          </v-col>
          </v-row>
          <v-btn @click="uploadEvent" :color="'blue'">Post</v-btn>
        </div>
      </div>
      <div class="content">
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
        <v-row class="pa-10">
            <v-col v-for="blog in blogEntriesFiltered" :key="blog.id" cols=3 class="m-0">
              <BlogEntry :blog="blog" @triggerLike="blog.isLiked = !blog.isLiked" @deletePost="deletePost"/>
            </v-col>
        </v-row>
      </div>
    </div>
</template>

<script setup lang="ts">
  import BlogEntry from './components/BlogEntry.vue';
  import { ref, reactive, computed, onMounted } from 'vue';
  
  type Blog = {
    id: number;
    title: string;
    content: string;
    isLiked: boolean;
    pictures: any[];
  };

  const searchField = ref(''); 
  const titleField = ref(''); 
  const storyField = ref(''); 
  const imageField = ref(''); 
  const baseUrl = "http://192.168.1.35:8080";
  
  const blogEntries: Blog[] = reactive([]);
  const blogEntriesFiltered = computed( () => blogEntries.filter(b => !searchField.value.length || b.content.includes(searchField.value)));
  const blogExample: Blog = reactive(
    {
      id: 0,
      title: 'Dolor',
      content: 'blablabla',
      isLiked: false,
      pictures: []
    }
  );

  function initDummyBlogList()
  {
    [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14].forEach(i => blogEntries.push( structuredClone({...blogExample, content: blogExample.content + ' ' + i, id: i}) ))
  }

  onMounted( async () => {
    let resp: any[] = await getMessages();

    if (!resp.length)
    {
      initDummyBlogList();
      console.log(`initialized dummy blog list: `, blogEntries);
    }
    else
    {
      resp.forEach( (it, index) => blogEntries.push( {...it, content: it.content + ' ' + index, title: 'Sample message #' + index, isLiked: false} ));
      console.log(`initialized blog list from backend data: `, blogEntries);
    }

  });

  // Task
  // function isFiltered(blog: any) {
  //   return !searchField.value.length || blog.description.includes(searchField.value);
  // }

  async function getMessages() {
    const response = await fetch(baseUrl + '/messages');
    return await response.json();
  }

  function deletePost(_: any, blogId: Number) {
    blogEntries.splice(blogEntries.findIndex((blog: Blog) => blog.id === blogId), 1);
  }

  const uploadEvent = ()=>{
    if(titleField.value.trim().length == 0) {
      alert('Title too short')
      return;
    }
    if(storyField.value.trim().length == 0) {
      alert('Story too short')
      return;
    }
    console.log(imageField.value)
    blogEntries.push(structuredClone({...blogExample, title:titleField.value,  description: storyField.value + ' ' + (blogEntries.length+1)}));
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
.content{
  flex: 1;
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
