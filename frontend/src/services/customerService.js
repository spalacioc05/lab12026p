import { apiClient } from './apiClient'

export const customerService = {
  async getCustomers() {
    const { data } = await apiClient.get('/api/customers')
    return data
  },

  async getCustomerById(id) {
    const { data } = await apiClient.get(`/api/customers/${id}`)
    return data
  },

  async getCustomerByAccountNumber(accountNumber) {
    const { data } = await apiClient.get(`/api/customers/account/${accountNumber}`)
    return data
  },

  async createCustomer(payload) {
    const { data } = await apiClient.post('/api/customers', payload)
    return data
  },
}
