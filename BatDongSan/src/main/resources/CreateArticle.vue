<template>
  <div class="article-container">
    <div class="admin-header">
      Create article
      <i class="fa-solid fa-circle-exclamation"></i>
    </div>
    <div class="admin-content-detail">
      <div class="row content-one-row-container">
        <div class="col-sm-3 d-flex  align-items-center">
          <div>Name</div>
          &nbsp;
          &nbsp;
          <div>
            <icon-vue name="help-circle"></icon-vue>
          </div>
        </div>
        <div class="col-sm-9">
          <input class="form-control" />
        </div>
      </div>

      <div class="row content-one-row-container">
        <div class="col-sm-3 d-flex  align-items-center" style="max-height: 38px">
          <div>Image preview</div>
          &nbsp;
          &nbsp;
          <div>
            <icon-vue name="help-circle"></icon-vue>
          </div>
        </div>
        <div class="col-sm-9 d-flex justify-content-between">
          <v-btn @click="clickUploadImage">Upload preview image</v-btn>
          <input id="input-upload" style="display: none" type="file" accept="image/*" class="form-control" />
          <div class="preview-image-container w-75 d-flex justify-content-center border rounded">
            <img id="preview-image"  src="" alt="" />
          </div>
        </div>
      </div>
      <div class="row content-one-row-container">
        <Editor
          api-key="vbdc9ujug4tzy8g1hxuu0xy4iff0djt7bop1uqd7vhrqz634"
          :init="{
            toolbar_mode: 'sliding',
            plugins: 'image anchor autolink charmap codesample emoticons link lists media searchreplace table visualblocks wordcount checklist mediaembed casechange export formatpainter pageembed linkchecker a11ychecker tinymcespellchecker permanentpen powerpaste advtable advcode editimage advtemplate ai mentions tinycomments tableofcontents footnotes mergetags autocorrect typography inlinecss',
            toolbar: 'undo redo | blocks fontfamily fontsize  | bold italic underline strikethrough | align lineheight | link image media table   | checklist numlist bullist indent outdent | emoticons charmap | removeformat',
            tinycomments_mode: 'embedded',
            tinycomments_author: 'Author name',
            image_dimensions: false,
            image_description: false,
            mergetags_list: [
              { value: 'First.Name', title: 'First Name' },
              { value: 'Email', title: 'Email' },
            ],
            height: '500',
            branding: false,
            ai_request: (request, respondWith) => respondWith.string(() => Promise.reject('See docs to implement AI Assistant')),
            file_picker_callback:  filePickerCallback
          }"
        />
      </div>
      <div class="row content-one-row-container ">
        <div class="d-flex justify-content-center">
          <v-btn @click="showModalCreatePost">Create</v-btn>
          <i class="fa-solid fa-triangle-exclamation"></i>        
        </div>
      </div>
    </div>
    <admin-confirm-modal
      :maxWidth="440"
      prependIcon="fa-solid fa-pen"
      text="Do you want to create article"
      title="Create Article"
      :show="showModal"
      @actionConfirm="actionConfirm"
    ></admin-confirm-modal>
  </div>
</template>

<script>
import Editor from '@tinymce/tinymce-vue'
import http from '@/common/http'
import path from '@/constant/path'
import responseCode from '@/constant/responseCode'
import AdminConfirmModal from '@/components/admin/AdminConfirmModal.vue'
import IconVue from '@/components/Icon.vue'
export default {
  name: 'CreateArticle',
  components: {
    Editor,
    'admin-confirm-modal': AdminConfirmModal,
    'icon-vue': IconVue
  },
  data: function () {
    return {
      inputUploadId: 'input-upload',
      previewImageId: 'preview-image',
      tinymce: null,
      dialog: false,
      showModal: false,
      helpCircle: null
    }
  },
  mounted: function () {
    const vm = this
    vm.addEvent()
  },
  methods: {
    clickUploadImage: function () {
      const vm = this
      document.getElementById(vm.inputUploadId).click()
    },
    addEvent: function () {
      const vm = this
      const input = document.getElementById(vm.inputUploadId)
      const previewImage = document.getElementById(vm.previewImageId)
      input.addEventListener('change', (e) => {
        const files = e.target.files
        if (files.length) {
          const src = URL.createObjectURL(files[0])
          previewImage.src = src
        }
      })
    },
    uploadImages: function (e) {
      console.log(e)
    },
    filePickerCallback: function (callback, value, meta) {
      const input = document.createElement('input')
      input.setAttribute('type', 'file')
      input.setAttribute('accept', 'image/*')
      input.onchange = (e) => {
        const files = e.target.files
        if (files.length) {
          const formData = new FormData()
          formData.append('file', files[0])
          http.POST(path.IMAGE_EDITOR_INSERT, formData, res => {
            if (res.code == responseCode.OK) {
              callback(res.data)
            }
          })
        }
      }
      input.click()
    },
    stringToSlug: function (str) {
      const from = 'àáãảạăằắẳẵặâầấẩẫậèéẻẽẹêềếểễệđùúủũụưừứửữựòóỏõọôồốổỗộơờớởỡợìíỉĩịäëïîöüûñçýỳỹỵỷ'
      const to   = 'aaaaaaaaaaaaaaaaaeeeeeeeeeeeduuuuuuuuuuuoooooooooooooooooiiiiiaeiiouuncyyyyy'
      debugger
      for (let i = 0, l = from.length; i < l ;i++) {
        str = str.replace(RegExp(from[i], "gi"), to[i]);
      }

      str = str.toLowerCase()
            .trim()
            .replace(/[^a-z0-9\-]/g, '-')
            .replace(/-+/g, '-');

      return str;
    },
    showModalCreatePost: function () {
      const vm = this
      vm.dialog = true
      vm.showModal = true
    },
    createArticle: function () {
      
    },
    actionConfirm: function (action) {
      const vm = this
      debugger
      if (action === 'ok') {
        vm.createArticle()
      } else {
        console.log(123);
      }
      vm.showModal = false
    }
  }
}
</script>

<style scoped>
#preview-image {
  max-height: 250px;
}
.preview-image-container {
  min-height: 250px;
}
</style>