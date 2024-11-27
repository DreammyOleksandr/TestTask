'use client'

import Link from 'next/link'

export default function NotFoundPage() {
  return (
    <div className='container-fluid min-vh-100 d-flex justify-content-center align-items-center bg-dark text-white'>
      <div className='text-center'>
        <h1 className='display-1 fw-bold'>404</h1>
        <h2 className='display-4'>Oops! Page Not Found</h2>
        <p className='lead'>The page you are looking for does not exist or has been moved.</p>
        <Link href='/' className='btn btn-light btn-lg mt-4'>
          Go Back to Home
        </Link>
      </div>
    </div>
  )
}
