import { get } from '@/utils/request'

export default {
  list: query => get('/api/admin/video/list', query)
}
