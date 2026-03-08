import axios from 'axios'

const apiBaseURL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080'

export const apiClient = axios.create({
  baseURL: apiBaseURL,
  timeout: 10000,
  headers: {
    'Content-Type': 'application/json',
  },
})

apiClient.interceptors.response.use(
  (response) => response,
  (error) => {
    const apiError = error?.response?.data
    const validationError = apiError?.validations
      ? Object.values(apiError.validations)[0]
      : null

    const message =
      validationError || apiError?.message || error.message || 'Error inesperado al conectar con el backend'

    return Promise.reject(new Error(message))
  },
)
