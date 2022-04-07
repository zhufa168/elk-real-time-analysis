import request from '@/utils/request'

// 查询列表
export function assetsList(query) {
  return request({
    url: '/search/assets/list',
    method: 'get',
    params: query
  })
}
