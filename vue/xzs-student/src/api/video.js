import { get } from '@/utils/request'

export default {
  list: () => get('/api/student/video/list')
}
