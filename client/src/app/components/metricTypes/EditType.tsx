'use client'

import { useState, useEffect } from 'react'
import { useRouter, useParams } from 'next/navigation'

export default function EditType() {
  const [name, setName] = useState<string>('')
  const router = useRouter()
  const { id } = useParams()

  useEffect(() => {
    const fetchMetricType = async () => {
      try {
        const response = await fetch(`http://localhost:8080/metrictypes/${id}`)
        if (!response.ok) {
          throw new Error('Failed to fetch metric type')
        }
        const data = await response.json()
        setName(data.name)
      } catch (error) {
        console.error('Error fetching metric type:', error)
      }
    }

    if (id) {
      fetchMetricType()
    }
  }, [id])

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault()
    try {
      const response = await fetch(`http://localhost:8080/metrictypes/${id}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ name }),
      })
      if (!response.ok) {
        throw new Error('Failed to update metric type')
      }
      router.push('/')
    } catch (error) {
      console.error('Error updating metric type:', error)
    }
  }

  return (
    <div className='container mt-5'>
      <h1>Edit Metric Type</h1>
      <form onSubmit={handleSubmit}>
        <div className='mb-3'>
          <label htmlFor='name' className='form-label'>
            Name
          </label>
          <input
            type='text'
            id='name'
            className='form-control'
            value={name}
            onChange={(e) => setName(e.target.value)}
            required
          />
        </div>
        <button type='submit' className='btn btn-primary'>
          Update
        </button>
      </form>
    </div>
  )
}
