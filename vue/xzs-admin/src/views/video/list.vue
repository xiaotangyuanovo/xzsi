<template>
  <div class="app-container">
    <div style="margin-bottom:20px;">
      <el-input v-model="title" placeholder="视频标题" style="width:200px;margin-right:10px" />
      <el-upload :action="uploadUrl" :data="{title: title}" :show-file-list="false"
                 :with-credentials="true" :headers="{ 'request-ajax': true }" @success="handleSuccess">
        <el-button type="primary">上传视频</el-button>
      </el-upload>
    </div>
    <el-table :data="list" style="width:100%">
      <el-table-column prop="id" label="ID" width="60" />
      <el-table-column prop="title" label="标题" />
      <el-table-column label="操作" width="120">
        <template slot-scope="scope">
          <el-button type="text" @click="play(scope.row)">播放</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="dialogVisible" width="60%" title="播放">
      <video v-if="current" :src="playUrl(current.id)" controls style="width:100%" />
    </el-dialog>
  </div>
</template>

<script>
import api from '@/api/video'
export default {
  name: 'VideoList',
  data () {
    return {
      list: [],
      title: '',
      dialogVisible: false,
      current: null,
      uploadUrl: (process.env.VUE_APP_URL || '') + '/api/admin/video/upload'
    }
  },
  created () {
    this.load()
  },
  methods: {
    load () {
      api.list().then(r => { this.list = r.response || [] })
    },
    handleSuccess () {
      this.$message.success('上传成功')
      this.load()
    },
    play (row) {
      this.current = row
      this.dialogVisible = true
    },
    playUrl (id) {
      return (process.env.VUE_APP_URL || '') + '/api/admin/video/stream/' + id
    }
  }
}
</script>
