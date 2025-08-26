<template>
  <div class="app-container">
    <el-table :data="list" style="width:100%">
      <el-table-column prop="title" label="标题" />
      <el-table-column label="播放" width="120">
        <template slot-scope="scope">
          <el-button type="text" @click="play(scope.row)">播放</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog :visible.sync="dialogVisible" width="90%" title="播放">
      <video v-if="current" :src="playUrl(current.id)" controls style="width:100%" />
    </el-dialog>
  </div>
</template>

<script>
import api from '@/api/video'
export default {
  name: 'VideoIndex',
  data () {
    return {
      list: [],
      dialogVisible: false,
      current: null
    }
  },
  created () {
    this.load()
  },
  methods: {
    load () {
      api.list().then(r => { this.list = r.response || [] })
    },
    play (row) {
      this.current = row
      this.dialogVisible = true
    },
    playUrl (id) {
      return process.env.VUE_APP_URL + '/api/student/video/stream/' + id
    }
  }
}
</script>
