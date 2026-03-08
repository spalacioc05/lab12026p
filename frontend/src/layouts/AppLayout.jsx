import { motion } from 'framer-motion'
import { Outlet, useLocation } from 'react-router-dom'
import { Navbar } from '../components/common/Navbar'

export function AppLayout() {
  const location = useLocation()

  return (
    <div className="min-h-screen bg-grid-light bg-[size:26px_26px]">
      <Navbar />
      <main className="mx-auto w-full max-w-7xl px-4 py-8 md:px-8">
        <motion.div
          key={location.pathname}
          initial={{ opacity: 0, y: 16 }}
          animate={{ opacity: 1, y: 0 }}
          transition={{ duration: 0.3 }}
        >
          <Outlet />
        </motion.div>
      </main>
    </div>
  )
}
