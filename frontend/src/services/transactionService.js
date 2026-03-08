import { apiClient } from './apiClient'

export const transactionService = {
  async transferMoney(payload) {
    const { data } = await apiClient.post('/api/transactions/transfer', payload)
    return data
  },

  async getAccountTransactions(accountNumber) {
    const { data } = await apiClient.get(`/api/transactions/account/${accountNumber}`)
    return data
  },
}
