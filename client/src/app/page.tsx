'use client'

import { useState, useEffect } from 'react'
import { Dropdown } from './components/metricTypes/Dropdown'
import { Table } from './components/metricValues/Table'
import Link from 'next/link'
import {
  fetchMetricTypes,
  fetchMetricValues,
  createDefaultMetricTypes,
  deleteMetricTypes,
} from './utils/api'

interface ErrorResponse {
  code: string
  details: string
  message: string
}

export default function Home() {
  const [metricValues, setMetricValues] = useState([])
  const [metricTypes, setMetricTypes] = useState([])
  const [errorMessage, setErrorMessage] = useState<string | null>(null)

  const fetchData = async () => {
    try {
      const [types, values] = await Promise.all([fetchMetricTypes(), fetchMetricValues()])
      setMetricTypes(types)
      setMetricValues(values)
    } catch (error) {
      console.error('Error fetching data:', error)
    }
  }

  const handleCreateDefaultTypes = async () => {
    try {
      await createDefaultMetricTypes()
      console.log('Default types created')
      fetchData()
      setErrorMessage(null)
    } catch (error: unknown) {
      console.error('Error creating default types:', error)

      if (isErrorResponse(error)) {
        setErrorMessage(error.details)
      } else {
        setErrorMessage('To create default types you must delete all types')
      }
    }
  }

  const handleDeleteTypes = async () => {
    try {
      await deleteMetricTypes()
      console.log('Types deleted')
      fetchData()
    } catch (error) {
      console.error('Error deleting types:', error)
    }
  }

  const handleDeleteValue = async (id: number) => {
    try {
      const response = await fetch(`http://localhost:8080/metricvalues/${id}`, {
        method: 'DELETE',
      })
      if (!response.ok) throw new Error('Failed to delete value')
      console.log(`Metric value ${id} deleted`)
      fetchData()
    } catch (error) {
      console.error(`Error deleting metric value ${id}:`, error)
    }
  }

  useEffect(() => {
    fetchData()
  }, [])

  return (
    <div className='container mt-5'>
      {errorMessage && <div className='alert alert-warning'>{errorMessage}</div>}

      <div className='row mb-5'>
        <div className='row'>
          <div className='col-md-4'>
            <button onClick={handleCreateDefaultTypes} className='btn btn-success w-100 mb-2'>
              Create Default Types
            </button>
          </div>
          <div className='col-md-4'>
            <button onClick={handleDeleteTypes} className='btn btn-danger w-100'>
              Delete All Types
            </button>
          </div>
          <div className='col-md-4'>
            <Dropdown metricTypes={metricTypes} />
          </div>
        </div>
      </div>

      <div className='row'>
        <div>
          <h1 className='mb-4'>Metric Values Dashboard</h1>
        </div>
        <div>
          <Link href={`/values/create`} className='btn btn-success'>
            Create
          </Link>
        </div>
      </div>
      <Table metricValues={metricValues} onDelete={handleDeleteValue} />
    </div>
  )
}

// Type guard to check if the error is of type ErrorResponse
function isErrorResponse(error: unknown): error is ErrorResponse {
  return (
    typeof error === 'object' &&
    error !== null &&
    'details' in error &&
    typeof (error as ErrorResponse).details === 'string'
  )
}
