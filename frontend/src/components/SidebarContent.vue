<script setup lang="ts">
import { ref } from 'vue'

const props = defineProps<{
    baseUrl: string;
    onPost: ()=>Promise<void>
}>()
const { baseUrl, onPost } = props;
const titleField = ref('');
const storyField = ref('');
const imageField = ref();

async function getBase64(file: any) {

    var reader = new FileReader();

    return new Promise((resolve) => {
        reader.onloadend = function () {
            resolve(reader.result);
        }
        reader.readAsDataURL(file);
    });
};
const uploadEvent = async () => {
    if (titleField.value.trim().length == 0) {
        alert('Title too short')
        return;
    }
    if (storyField.value.trim().length == 0) {
        alert('Story too short')
        return;
    }
    console.log('image field: ', imageField.value[0])

    let base64enc = await getBase64(imageField.value[0]);


    let body = {
        "content": titleField.value,
        "pictures": [
            {
                "content": base64enc,
                "name": imageField.value[0].name
            }
        ]
    }

    console.log(`body: `, body);

    const response = await fetch(baseUrl + "/messages", {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
        },
        body: JSON.stringify(body),
    });
    setTimeout(()=>{
        onPost();
    },1);
    
    console.log(`uploaded image resp: `, response);

    // blogEntries.push({...blogExample, title: titleField.value,  content: storyField.value + ' ' + (blogEntries.length+1)}); 
    // blogEntries.push(structuredClone({...blogExample, title: titleField.value,  content: storyField.value + ' ' + (blogEntries.length+1)})); Exercise 
}
</script>

<template>
    <div class="sidebar_content">
        <v-text-field 
            v-model="titleField" 
            clearable 
            hide-details 
            label="Enter title.." 
            variant="solo" 
            single-line
            density="compact" 
        />
        <v-textarea v-model="storyField" label="Story" auto-grow variant="solo"></v-textarea>
        <v-row>
            <v-col>
                <v-file-input v-model="imageField" label="Upload image" chips variant="solo" prepend-inner-icon="mdi-camera"
                    density="compact"></v-file-input>
            </v-col>
        </v-row>
        <v-btn @click="uploadEvent" :color="'blue'">Post</v-btn>
    </div>
</template>

<style scoped>

.sidebar_content{
  flex-direction: column;
  display: flex;
  padding: 12px;
  gap: 8px;
}
</style>