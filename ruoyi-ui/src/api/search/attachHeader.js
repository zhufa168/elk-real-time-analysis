import request from '@/utils/request'

// 查询列表
export function attachHeaderList(query) {
  return request({
    url: '/search/attachHeader/list',
    method: 'get',
    params: query
  })
}
