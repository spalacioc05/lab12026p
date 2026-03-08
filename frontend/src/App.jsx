import { Toaster } from 'react-hot-toast'
import { AppRouter } from './router/AppRouter'

function App() {
  return (
    <>
      <AppRouter />
      <Toaster
        position="top-right"
        toastOptions={{
          duration: 3500,
          style: {
            border: '1px solid #e2e8f0',
            borderRadius: '14px',
            background: '#ffffff',
            color: '#0f172a',
            boxShadow: '0 12px 30px -18px rgba(2,6,23,0.35)',
          },
        }}
      />
    </>
  )
}

export default App
