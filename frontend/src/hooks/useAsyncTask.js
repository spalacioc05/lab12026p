import { useCallback, useState } from 'react'

export function useAsyncTask(initialData = null) {
  const [data, setData] = useState(initialData)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState('')

  const run = useCallback(async (task) => {
    try {
      setLoading(true)
      setError('')
      const result = await task()
      setData(result)
      return result
    } catch (err) {
      const message = err instanceof Error ? err.message : 'Ocurrio un error inesperado'
      setError(message)
      throw err
    } finally {
      setLoading(false)
    }
  }, [])

  return {
    data,
    setData,
    loading,
    error,
    setError,
    run,
  }
}
